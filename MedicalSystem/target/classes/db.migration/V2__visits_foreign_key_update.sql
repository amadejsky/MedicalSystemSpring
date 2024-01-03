alter table visits
    add foreign key (patient_id) references patients (id);