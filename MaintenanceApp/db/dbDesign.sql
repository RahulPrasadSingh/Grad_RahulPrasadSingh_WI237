CREATE TABLE users (
    user_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL,
    
    CONSTRAINT chk_role CHECK (role IN ('ADMIN', 'OWNER'))
);

CREATE TABLE sites (
    site_id VARCHAR(10) PRIMARY KEY,
    length DOUBLE PRECISION NOT NULL,
    width DOUBLE PRECISION NOT NULL,
    occupied BOOLEAN NOT NULL,
    type VARCHAR(20) NOT NULL,
    owner_id VARCHAR(10),
    
    CONSTRAINT chk_type CHECK (type IN ('VILLA', 'APARTMENT', 'INDEPENDENT_HOUSE', 'OPEN_SITE')),
    CONSTRAINT fk_sites_owner FOREIGN KEY (owner_id) 
        REFERENCES users(user_id) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);

CREATE TABLE maintenance (
    maintenance_id VARCHAR(10) PRIMARY KEY,
    site_id VARCHAR(10) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    paid BOOLEAN NOT NULL,
    requested BOOLEAN NOT NULL,
    collected_by VARCHAR(10),
    collected_on DATE,
    
    CONSTRAINT fk_maintenance_site FOREIGN KEY (site_id) 
        REFERENCES sites(site_id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_maintenance_collector FOREIGN KEY (collected_by) 
        REFERENCES users(user_id) 
        ON DELETE SET NULL 
        ON UPDATE CASCADE
);

INSERT INTO users (user_id, name, password, role) VALUES
('A0001', 'Admin One', 'admin123', 'ADMIN'),
('A0002', 'Admin Two', 'admin456', 'ADMIN');