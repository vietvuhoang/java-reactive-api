CREATE DATABASE IF NOT EXISTS iot_devices;
USE iot_devices;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS devices (
    id VARCHAR(64) PRIMARY KEY DEFAULT uuid_generate_v4(),
    production_code VARCHAR(64) NOT NULL,
    device_type VARCHAR(64) NOT NULL,
    serial_number VARCHAR(64) NOT NULL UNIQUE,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX device_production_code_index ON devices(production_code);
CREATE INDEX device_serial_number_index ON devices(serial_number);
CREATE INDEX device_device_type_index ON devices(device_type);

CREATE TABLE IF NOT EXISTS device_events (
    id VARCHAR(64) PRIMARY KEY DEFAULT uuid_generate_v4(),
    device_id VARCHAR(64) NOT NULL,
    event_type VARCHAR(64) NOT NULL,
    payload VARCHAR(255) NOT NULL,
    event_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_device FOREIGN KEY(device_id)  REFERENCES devices(id) ON DELETE CASCADE
);

CREATE INDEX event_device_id_index ON device_events(device_id);
CREATE INDEX event_timestamp_index ON device_events(event_timestamp);
CREATE INDEX event_type_index ON device_events(event_type);
