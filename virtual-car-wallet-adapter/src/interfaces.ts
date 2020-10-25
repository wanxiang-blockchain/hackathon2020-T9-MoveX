
import { HttpService } from '@nestjs/common';

export interface ConfigService {
    getOrThrow(key: string);
}

export enum EventType {
    INIT = 'INIT',
    OPEN = 'OPEN',
    CLOSE = 'CLOSE',
    PERIODIC = 'PERIODIC'
}

export interface VehicleBackendAdapter {
    /**
     * Initialize the adapter, called once during startup of the application.
     * @param httpService the HTTP service that can be used to perform HTTP calls
     * @param configService the configuration service
     */
    init(httpService: HttpService , configService: ConfigService): Promise<void>
  
    /**
     * Trigger a specific operation for a vehicle in the vehicle backend.
     * @param vin the VIN of the vehicle
     * @param eventType the event type that marks the operation
     */
    triggerVehicleBackend(vin: string, eventType: EventType): Promise<void>
  }