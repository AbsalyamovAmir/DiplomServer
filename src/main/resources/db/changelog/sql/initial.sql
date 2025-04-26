CREATE TABLE vulnerabilities (
    id BIGSERIAL PRIMARY KEY,
    identifier VARCHAR(50) NOT NULL UNIQUE,
    name TEXT NOT NULL,
    description TEXT,
    identify_date DATE,
    severity TEXT,
    solution TEXT,
    vul_status VARCHAR(100),
    exploit_status VARCHAR(100),
    fix_status VARCHAR(100),
    other TEXT,
    vul_incident BOOLEAN,
    vul_class VARCHAR(100),
    vul_state VARCHAR(100)
);

CREATE TABLE vulnerable_software (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    vendor VARCHAR(255),
    name TEXT,
    version VARCHAR(100),
    platform VARCHAR(100),
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE software_types (
    id BIGSERIAL PRIMARY KEY,
    software_id INT NOT NULL,
    type VARCHAR(100) NOT NULL,
    FOREIGN KEY (software_id) REFERENCES vulnerable_software(id) ON DELETE CASCADE
);

CREATE TABLE environment (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE os (
    id BIGSERIAL PRIMARY KEY,
    environment_id INT NOT NULL,
    vendor VARCHAR(255),
    name VARCHAR(100),
    version VARCHAR(50),
    platform VARCHAR(50),
    FOREIGN KEY (environment_id) REFERENCES environment(id) ON DELETE CASCADE
);

CREATE TABLE cwes (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE cwe (
    id BIGSERIAL PRIMARY KEY,
    cwes_id INT NOT NULL,
    identifier VARCHAR(20) NOT NULL,
    name VARCHAR(255),
    FOREIGN KEY (cwes_id) REFERENCES cwes(id) ON DELETE CASCADE
);

CREATE TABLE cvss (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    vector VARCHAR(100),
    score DECIMAL(3,1),
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE cvss3 (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    vector VARCHAR(100),
    score DECIMAL(3,1),
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE sl_oper_procs (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT,
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE sop (
    id BIGSERIAL PRIMARY KEY,
    sl_oper_procs_id INT NOT NULL,
    name VARCHAR(255),
    FOREIGN KEY (sl_oper_procs_id) REFERENCES sl_oper_procs(id) ON DELETE CASCADE
);

CREATE TABLE sources (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    source TEXT,
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);

CREATE TABLE identifiers (
    id BIGSERIAL PRIMARY KEY,
    vulnerability_id INT NOT NULL,
    type VARCHAR(50),
    value VARCHAR(100),
    link TEXT,
    FOREIGN KEY (vulnerability_id) REFERENCES vulnerabilities(id) ON DELETE CASCADE
);