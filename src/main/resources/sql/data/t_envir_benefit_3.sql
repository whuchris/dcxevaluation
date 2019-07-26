#景观类环境效益
CREATE TABLE t_envir_benefit_3
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    art FLOAT(5) NOT NULL,
    project_function FLOAT(5) NOT NULL,
    project_technology FLOAT(5) NOT NULL,
    envir_friendliness FLOAT(5) NOT NULL,
    state INT(1) NOT NULL,
    CONSTRAINT pk_t_envir_benefit_3_id PRIMARY KEY (id),
    CONSTRAINT fk_t_envir_benefit_3_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_envir_benefit_3_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_envir_benefit_3_state CHECK (state IN(1,2))
);

SELECT * FROM t_envir_benefit_3;
DELETE FROM t_envir_benefit_3 WHERE id = 1;