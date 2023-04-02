INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (1, 'Tallinn-Harku', 26038, -5, 12, 'Light snow shower', '2023-01-01 14:15:00');
INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (2, 'Tartu-Tõravere', 26242, -5, 22, 'snow', '2023-01-01 14:15:00');
INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (3, 'Pärnu', 41803, -5, 8, 'thunder', '2023-01-01 14:15:00');

INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (4, 'Tallinn-Harku', 26038, 20.5, 3.6, 'rain', '2022-07-17 14:15:00');
INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (5, 'Tartu-Tõravere', 26242, 20.5, 3.6, '', '2022-07-17 14:15:00');
INSERT INTO weather_data (id, name, WMOCode, air_temperature, wind_speed, weather_phenomenon, timestamp)
VALUES (6, 'Pärnu', 41803, 20.5, 3.6, '', '2022-07-17 14:15:00');


INSERT INTO Regional_Base_Fee (id, city, carRBF, scooterRBF, bikeRBF)
VALUES (1, 'Tallinn', 4, 3.5, 3);
INSERT INTO Regional_Base_Fee (id, city, carRBF, scooterRBF, bikeRBF)
VALUES (2, 'Tartu', 3.5, 3, 2.5);
INSERT INTO Regional_Base_Fee (id, city, carRBF, scooterRBF, bikeRBF)
VALUES (3, 'Pärnu', 3, 2.5, 2);


INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (1, 'scooter', 'ATEF', null, '-10.01', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (2, 'scooter', 'ATEF', '-10', '0', 0.5, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (3, 'bike', 'ATEF', null, '-10.01', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (4, 'bike', 'ATEF', '-10', '0', 0.5, null);

INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (5, 'bike', 'WSEF', '10', '20', 0.5, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (6, 'bike', 'WSEF', '20.01', null, null, 'Usage of selected vehicle type is forbidden');

INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (7, 'scooter', 'WPEF', 'snow', '', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (8, 'scooter', 'WPEF', 'sleet', '', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (9, 'bike', 'WPEF', 'snow', '', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (10, 'bike', 'WPEF', 'sleet', '', 1, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (11, 'scooter', 'WPEF', 'rain', '', 0.5, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (12, 'bike', 'WPEF', 'rain', '', 0.5, null);
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (13, 'scooter', 'WPEF', 'glaze', '', null, 'Usage of selected vehicle type is forbidden');
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (14, 'scooter', 'WPEF', 'hail', '', null, 'Usage of selected vehicle type is forbidden');
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (15, 'scooter', 'WPEF', 'thunder', '', null, 'Usage of selected vehicle type is forbidden');
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (16, 'bike', 'WPEF', 'glaze', '', null, 'Usage of selected vehicle type is forbidden');
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (17, 'bike', 'WPEF', 'hail', '', null, 'Usage of selected vehicle type is forbidden');
INSERT INTO extra_fee(id, vehicle_type, extra_fee_type, first_parameter, second_parameter, fee, error_message)
VALUES (18, 'bike', 'WPEF', 'thunder', '', null, 'Usage of selected vehicle type is forbidden');






