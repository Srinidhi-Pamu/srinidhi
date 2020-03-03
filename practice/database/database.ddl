use MLP203;
CREATE TABLE employees(
f_name VARCHAR(30) NOT NULL,
l_name VARCHAR(30) NOT NULL,
emp_id INT PRIMARY KEY NOT NULL,
dept VARCHAR(30) NOT NULL,
email VARCHAR(30) NOT NULL,
contact VARCHAR(15) NOT NULL
);
CREATE TABLE leaves(
leave_id INT PRIMARY KEY NOT NULL,
leave_from DATE NOT NULL,
leave_to DATE NOT NULL,
no_leavedays INT NOT NULL,
leave_applydate DATETIME NOT NULL,
leave_status VARCHAR(30) NOT NULL
);

alter table leaves add notification varchar(30) null;
alter table leaves add emp_id int ; 
alter table leaves add constraint fk_id foreign key(emp_id) references employees(emp_id);
