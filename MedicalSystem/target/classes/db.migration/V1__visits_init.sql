CREATE TABLE VISITS (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        patient_id BIGINT,
                        visit_date DATE,
                        FOREIGN KEY (patient_id) REFERENCES PATIENTS(id)
);
