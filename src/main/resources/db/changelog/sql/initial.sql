-- Create vendor table
CREATE TABLE vendor (
                        id serial PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        description TEXT
);

-- Create software_type table
CREATE TABLE software_type (
                               id serial PRIMARY KEY,
                               name VARCHAR(100) NOT NULL
);

-- Create operating_system table
CREATE TABLE operating_system (
                                  id serial PRIMARY KEY,
                                  vendor_id BIGINT,
                                  name VARCHAR(255) NOT NULL,
                                  version VARCHAR(100),
                                  platform VARCHAR(100),
                                  FOREIGN KEY (vendor_id) REFERENCES vendor(id)
);

-- Create software table
CREATE TABLE software (
                          id serial PRIMARY KEY,
                          vendor_id BIGINT,
                          name VARCHAR(255) NOT NULL,
                          version VARCHAR(100),
                          platform VARCHAR(100),
                          FOREIGN KEY (vendor_id) REFERENCES vendor(id)
);

-- Create junction table for software and software_type (many-to-many)
CREATE TABLE software_types (
                                software_id BIGINT NOT NULL,
                                type_id BIGINT NOT NULL,
                                PRIMARY KEY (software_id, type_id),
                                FOREIGN KEY (software_id) REFERENCES software(id),
                                FOREIGN KEY (type_id) REFERENCES software_type(id)
);

-- Create cwe table
CREATE TABLE cwe (
                     id serial PRIMARY KEY,
                     identifier VARCHAR(20) NOT NULL,
                     name VARCHAR(255) NOT NULL,
                     description TEXT
);

-- Create source table
CREATE TABLE source (
                        id serial PRIMARY KEY,
                        url VARCHAR(500) NOT NULL,
                        description TEXT
);

-- Create identifier table
CREATE TABLE identifier (
                            id serial PRIMARY KEY,
                            type VARCHAR(50) NOT NULL,
                            value VARCHAR(100) NOT NULL,
                            link VARCHAR(500)
);

-- Create vulnerability table
CREATE TABLE vulnerability (
                               id serial PRIMARY KEY,
                               identifier VARCHAR(50) NOT NULL UNIQUE,
                               name VARCHAR(500) NOT NULL,
                               description TEXT,
                               identify_date DATE,
                               cvss_vector VARCHAR(100),
                               cvss_score float,
                               cvss3_vector VARCHAR(100),
                               cvss3_score float,
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

-- Create junction table for vulnerability and software (many-to-many)
CREATE TABLE vulnerability_software (
                                        vulnerability_id BIGINT NOT NULL,
                                        software_id BIGINT NOT NULL,
                                        PRIMARY KEY (vulnerability_id, software_id),
                                        FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                        FOREIGN KEY (software_id) REFERENCES software(id)
);

-- Create junction table for vulnerability and operating_system (many-to-many)
CREATE TABLE vulnerability_os (
                                  vulnerability_id BIGINT NOT NULL,
                                  os_id BIGINT NOT NULL,
                                  PRIMARY KEY (vulnerability_id, os_id),
                                  FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                  FOREIGN KEY (os_id) REFERENCES operating_system(id)
);

-- Create junction table for vulnerability and cwe (many-to-many)
CREATE TABLE vulnerability_cwe (
                                   vulnerability_id BIGINT NOT NULL,
                                   cwe_id BIGINT NOT NULL,
                                   PRIMARY KEY (vulnerability_id, cwe_id),
                                   FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                   FOREIGN KEY (cwe_id) REFERENCES cwe(id)
);

-- Create junction table for vulnerability and source (many-to-many)
CREATE TABLE vulnerability_source (
                                      vulnerability_id BIGINT NOT NULL,
                                      source_id BIGINT NOT NULL,
                                      PRIMARY KEY (vulnerability_id, source_id),
                                      FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                      FOREIGN KEY (source_id) REFERENCES source(id)
);

-- Create junction table for vulnerability and identifier (many-to-many)
CREATE TABLE vulnerability_identifier (
                                          vulnerability_id BIGINT NOT NULL,
                                          identifier_id BIGINT NOT NULL,
                                          PRIMARY KEY (vulnerability_id, identifier_id),
                                          FOREIGN KEY (vulnerability_id) REFERENCES vulnerability(id),
                                          FOREIGN KEY (identifier_id) REFERENCES identifier(id)
);