#建筑类环境效益
CREATE TABLE t_envir_benefit_1
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    art FLOAT(5) NOT NULL,
    outdoor_envir FLOAT(5) NOT NULL,
    resource_utilization FLOAT(5) NOT NULL,
    indoor_envir FLOAT(5) NOT NULL,
    construction_management FLOAT(5) NOT NULL,
    operation_management FLOAT(5) NOT NULL,
    innovation_evaluation FLOAT(5) NOT NULL,
    state INT(1),
    CONSTRAINT pk_t_envir_benefit_1_id PRIMARY KEY (id),
    CONSTRAINT fk_t_envir_benefit_1_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_envir_benefit_1_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_envir_benefit_1_state CHECK (state IN(1,2))
);

DROP TABLE t_envir_benefit_1
SELECT * FROM t_envir_benefit_1;
DELETE FROM t_envir_benefit_1 WHERE id = 3;