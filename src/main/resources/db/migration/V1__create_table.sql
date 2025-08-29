BEGIN TRANSACTION;

CREATE TABLE member (
  id VARCHAR(36) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  plate_number VARCHAR(255) NOT NULL,
  member_expired_date TIMESTAMP NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP NOT NULL,
  updated_by VARCHAR(255) NOT NULL,
  updated_date TIMESTAMP NOT NULL,
  deleted_by VARCHAR(255),
  deleted_date TIMESTAMP
);

CREATE TABLE ticket (
    id VARCHAR(36) PRIMARY KEY,
    plate_number VARCHAR(16) NOT NULL,
    check_in_at TIMESTAMP WITH TIME ZONE NOT NULL,
    check_out_at TIMESTAMP WITH TIME ZONE NULL,
    total_price NUMERIC(19,2),
    status VARCHAR(10) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_by VARCHAR(255) NOT NULL,
    created_date TIMESTAMP NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    updated_date TIMESTAMP NOT NULL,
    deleted_by VARCHAR(255),
    deleted_date TIMESTAMP
);

CREATE TABLE voucher (
  id VARCHAR(10) NOT NULL PRIMARY KEY,
  discount DECIMAL NOT NULL,
  quantity INT NOT NULL,
  expiration_date TIMESTAMP NOT NULL,
  created_by VARCHAR(255) NOT NULL,
  created_date TIMESTAMP NOT NULL,
  updated_by VARCHAR(255) NOT NULL,
  updated_date TIMESTAMP NOT NULL,
  deleted_by VARCHAR(255),
  deleted_date TIMESTAMP
);


COMMIT;
