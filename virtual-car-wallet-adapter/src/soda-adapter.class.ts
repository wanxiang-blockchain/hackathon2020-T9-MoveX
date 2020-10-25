import { ConfigService, EventType, VehicleBackendAdapter } from './interfaces';
import { HttpService, Logger } from '@nestjs/common';

export class SodaAdapter implements VehicleBackendAdapter {
    private httpService: HttpService;
    private configService: ConfigService;
    private readonly logger = new Logger(SodaAdapter.name);

    async init(httpService: HttpService, configService: ConfigService) {
        this.httpService = httpService;
        this.configService = configService;
        this.logger.log('SODA Adapter initialized');
    }

    async triggerVehicleBackend(vin: string, eventType: EventType): Promise<void> {
        if (eventType === EventType.INIT || eventType === EventType.PERIODIC) {
            return;
          }
          const sodaUrl = this.configService.getOrThrow('sodaEndpoint');
          const sodaBody = { /* removed in order to not expose sensitive data */ };
          this.logger.debug('Sending door lock/unlock request to SODA using url ' + sodaUrl);
          try {
            const sodaResponse = await this.httpService.post(sodaUrl, sodaBody, {
              headers: {
                'X-App': this.configService.getOrThrow('sodaAppId'),
                'X-Key': this.configService.getOrThrow('sodaApiKey')
              }
            }).toPromise();
            this.logger.debug(`SODA response status: ${sodaResponse.status}`);
            // wait for the command to finish (max. 20 attempts)
            const commandStatusUrl = sodaResponse.headers.location;
            if (commandStatusUrl) {
              let statusWaitingAttempts = 0;
              while (statusWaitingAttempts < 20) {
                const statusResponse = await this.httpService.get(commandStatusUrl).toPromise();
                this.logger.debug('SODA command status: ' + JSON.stringify(statusResponse.data));
                const statusString = statusResponse.data.status;
                if (statusString === 'WAITING') {
                  await this.wait(1000);
                  statusWaitingAttempts++;
                } else {
                  if (statusString === 'FAILED') {
                    throw new Error('SODA command failed');
                  }
                  if (statusString === 'EXPIRED') {
                    throw new Error('SODA command expired');
                  }
                  return;
                }
              }
              throw new Error('Maximum attempts waiting for SODA command exceeded');
            }
          } catch (error) {
            this.logger.error(`SODA response Error: ${error.message}`);
            throw error;
          };
    }

    private wait(ms) {
        return new Promise((resolve, _) => {
            setTimeout(() => {
                resolve(ms)
            }, ms)
        })
    }
}