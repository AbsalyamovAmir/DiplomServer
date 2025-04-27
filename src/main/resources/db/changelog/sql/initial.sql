CREATE TABLE "vulnerability"
(
    "cvss3_score"    DOUBLE PRECISION NULL DEFAULT NULL,
    "cvss_score"     DOUBLE PRECISION NULL DEFAULT NULL,
    "hash"           INTEGER NULL DEFAULT NULL,
    "identify_date"  DATE NULL DEFAULT NULL,
    "vul_incident"   BOOLEAN NULL DEFAULT NULL,
    "id"             SERIAL PRIMARY KEY,
    "identifier"     VARCHAR(50)  NOT NULL,
    "cvss3_vector"   VARCHAR(100) NULL DEFAULT NULL,
    "cvss_vector"    VARCHAR(100) NULL DEFAULT NULL,
    "exploit_status" VARCHAR(100) NULL DEFAULT NULL,
    "fix_status"     VARCHAR(100) NULL DEFAULT NULL,
    "vul_class"      VARCHAR(100) NULL DEFAULT NULL,
    "vul_state"      VARCHAR(100) NULL DEFAULT NULL,
    "vul_status"     VARCHAR(100) NULL DEFAULT NULL,
    "name"           VARCHAR(500) NOT NULL,
    "description"    TEXT NULL DEFAULT NULL,
    "other"          TEXT NULL DEFAULT NULL,
    "severity"       VARCHAR(255) NULL DEFAULT NULL,
    "solution"       TEXT NULL DEFAULT NULL,
    UNIQUE ("identifier")
)
;

CREATE TABLE "vendor"
(
    "id"   SERIAL PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL
--     PRIMARY KEY ("id")
)
;

CREATE TABLE "software"
(
    "id"        SERIAL PRIMARY KEY,
    "vendor_id" BIGINT NULL DEFAULT NULL,
    "vul_id"    BIGINT NULL DEFAULT NULL,
    "platform"  VARCHAR(100) NULL DEFAULT NULL,
    "version"   VARCHAR(100) NULL DEFAULT NULL,
    "name"      VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk9x9xnga9p24ag27rpyakeqj9b" FOREIGN KEY ("vul_id") REFERENCES "vulnerability" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkhxr3i5qdgsvp6ow70lbkpdunc" FOREIGN KEY ("vendor_id") REFERENCES "vendor" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "source"
(
    "id"     SERIAL PRIMARY KEY,
    "vul_id" BIGINT NULL DEFAULT NULL,
    "url"    VARCHAR(500) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fkfbcv3pp7pl80b3okclv70xp9d" FOREIGN KEY ("vul_id") REFERENCES "vulnerability" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "software_type"
(
    "id"   SERIAL PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL,
    PRIMARY KEY ("id")
)
;

CREATE TABLE "software_types"
(
    "software_id" BIGINT NOT NULL,
    "type_id"     BIGINT NOT NULL,
    PRIMARY KEY ("software_id", "type_id"),
    CONSTRAINT "fkh6u93iswygitwkp702ioymyk4" FOREIGN KEY ("type_id") REFERENCES "software_type" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fkt098wrm49w0knxw1b6d1vhf46" FOREIGN KEY ("software_id") REFERENCES "software" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "operating_system"
(
    "id"        SERIAL PRIMARY KEY,
    "vendor_id" BIGINT NULL DEFAULT NULL,
    "vul_id"    BIGINT NULL DEFAULT NULL,
    "platform"  VARCHAR(100) NULL DEFAULT NULL,
    "version"   VARCHAR(100) NULL DEFAULT NULL,
    "name"      VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk1jgt7uyqdqr2enpod9d08d4dq" FOREIGN KEY ("vul_id") REFERENCES "vulnerability" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "fko5rbap07bfle5shupcsweug0d" FOREIGN KEY ("vendor_id") REFERENCES "vendor" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE TABLE "cwe"
(
    "id"          SERIAL PRIMARY KEY,
    "vul_id"      BIGINT NULL DEFAULT NULL,
    "identifier"  VARCHAR(20)  NOT NULL,
    "description" TEXT NULL DEFAULT NULL,
    "name"        VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fkmgcde9gwibblv04pf1ql6q4h1" FOREIGN KEY ("vul_id") REFERENCES "vulnerability" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
;

CREATE INDEX idx_vulnerability_hash ON vulnerability (hash);