#室内类环境效益
CREATE TABLE t_envir_benefit_4
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    cultural_envir FLOAT(5) NOT NULL,
    physical_envir FLOAT(5) NOT NULL,
    decoration_material FLOAT(5) NOT NULL,
    decoration_technology FLOAT(5) NOT NULL,
    state INT(1) NOT NULL,
    CONSTRAINT pk_t_envir_benefit_4_id PRIMARY KEY (id),
    CONSTRAINT fk_t_envir_benefit_4_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_envir_benefit_4_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_envir_benefit_4_state CHECK (state IN(1,2))
);

SELECT * FROM t_envir_benefit_4;
delete from t_envir_benefit_4 where id = 1;