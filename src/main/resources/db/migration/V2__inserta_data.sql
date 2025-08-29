BEGIN TRANSACTION;

INSERT INTO member (id, name, plate_number, member_expired_date,
                    created_by, created_date, updated_by, updated_date)
VALUES
(gen_random_uuid(), 'Andi Setiawan', 'B1234XYZ',
 NOW() + INTERVAL '30 days', 'system', NOW(), 'system', NOW()),
(gen_random_uuid(), 'Budi Hartono', 'D5678ABC',
 NOW() + INTERVAL '60 days', 'system', NOW(), 'system', NOW()),
(gen_random_uuid(), 'Citra Dewi', 'F9999DEF',
 NOW() + INTERVAL '15 days', 'system', NOW(), 'system', NOW());

INSERT INTO ticket (id, plate_number, check_in_at, check_out_at,
                    total_price, status, active,
                    created_by, created_date, updated_by, updated_date)
VALUES
(gen_random_uuid(), 'B1234XYZ',
 NOW() - INTERVAL '2 hours', NULL,
 NULL, 'OPEN', TRUE,
 'system', NOW(), 'system', NOW()),
(gen_random_uuid(), 'D5678ABC',
 NOW() - INTERVAL '5 hours', NOW() - INTERVAL '2 hours',
 9000.00, 'CLOSED', FALSE,
 'system', NOW(), 'system', NOW()),


(gen_random_uuid(), 'F9999DEF',
 NOW() - INTERVAL '30 minutes', NULL,
 NULL, 'OPEN', TRUE,
 'system', NOW(), 'system', NOW());


INSERT INTO voucher (id, discount, quantity, expiration_date,
                     created_by, created_date, updated_by, updated_date)
VALUES
('DISC5K',  5000.00, 100, NOW() + INTERVAL '30 days', 'system', NOW(), 'system', NOW()),
('DISC10K', 10000.00, 50,  NOW() + INTERVAL '60 days', 'system', NOW(), 'system', NOW()),
('DISC20K', 20000.00, 10,  NOW() + INTERVAL '7 days',  'system', NOW(), 'system', NOW());


COMMIT;
