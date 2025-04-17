-- Vendor table
CREATE TABLE vendor (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        description TEXT
);

-- Software table
CREATE TABLE software (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          vendor_id BIGINT,
                          name VARCHAR(255) NOT NULL,
                          version VARCHAR(100),
                          platform VARCHAR(100),
                          FOREIGN KEY (vendor_id) REFERENCES vendor(id)
);

-- Software Type table
CREATE TABLE software_type (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(100) NOT NULL
);

-- Software to Type mapping
CREATE TABLE software_types (
                                software_id BIGINT,
                                type_id BIGINT,
                                PRIMARY KEY (software_id, type_id),
                                FOREIGN KEY (software_id) REFERENCES software(id),
                                FOREIGN KEY (type_id) REFERENCES software_type(id)
);

-- Operating System table
CREATE TABLE operating_system (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  vendor_id BIGINT,
                                  name VARCHAR(255) NOT NULL,
                                  version VARCHAR(100),
                                  platform VARCHAR(100),
                                  FOREIGN KEY (vendor_id) REFERENCES vendor(id)
);

-- CWE table
CREATE TABLE cwe (
                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                     identifier VARCHAR(20) NOT NULL,
                     name VARCHAR(255) NOT NULL,
                     description TEXT
);

-- Vulnerability table
CREATE TABLE vulnerability (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               identifier VARCHAR(50) NOT NULL UNIQUE,
                               name VARCHAR(500) NOT NULL,
                               description TEXT,
                               identify_date DATE,
                               cvss_vector VARCHAR(100),
                               cvss_score DECIMAL(3,1),
                               cvss3_vector VARCHAR(100),
                               cvss3_score DECIMAL(3,1),
                               severity VARCHAR(255),
                               solution TEXT,
                               vul_status VARCHAR(100),
                               exploit_status VARCHAR(100),
                               fix_status VARCHAR(100),
                               other TEXT,
                               vul_incident BOOLEAN,
                               vul_class VARCHAR(100),
                               vul_state VARCHAR(100)
);

-- Vulnerability to Software mapping
CREATE TABLE vulnerability_software (
                                        vulnerability_id BIGINT,
                                        software_id BIGINT,
                                        PRIMARY KEY (vulnerability_id, software_id),
                                        FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                        FOREIGN KEY (software_id) REFERENCES software(id)
);

-- Vulnerability to OS mapping
CREATE TABLE vulnerability_os (
                                  vulnerability_id BIGINT,
                                  os_id BIGINT,
                                  PRIMARY KEY (vulnerability_id, os_id),
                                  FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                  FOREIGN KEY (os_id) REFERENCES operating_system(id)
);

-- Vulnerability to CWE mapping
CREATE TABLE vulnerability_cwe (
                                   vulnerability_id BIGINT,
                                   cwe_id BIGINT,
                                   PRIMARY KEY (vulnerability_id, cwe_id),
                                   FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                   FOREIGN KEY (cwe_id) REFERENCES cwe(id)
);

-- Source table
CREATE TABLE source (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        url VARCHAR(500) NOT NULL,
                        description TEXT
);

-- Vulnerability to Source mapping
CREATE TABLE vulnerability_source (
                                      vulnerability_id BIGINT,
                                      source_id BIGINT,
                                      PRIMARY KEY (vulnerability_id, source_id),
                                      FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                      FOREIGN KEY (source_id) REFERENCES source(id)
);

-- Identifier table
CREATE TABLE identifier (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            type VARCHAR(50) NOT NULL,
                            value VARCHAR(100) NOT NULL,
                            link VARCHAR(500)
);

-- Vulnerability to Identifier mapping
CREATE TABLE vulnerability_identifier (
                                          vulnerability_id BIGINT,
                                          identifier_id BIGINT,
                                          PRIMARY KEY (vulnerability_id, identifier_id),
                                          FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                          FOREIGN KEY (identifier_id) REFERENCES identifier(id)
);