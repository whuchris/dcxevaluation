#规划类环境效益
CREATE TABLE t_envir_benefit_2
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    art FLOAT(5) NOT NULL,
    land_using FLOAT(5) NOT NULL,
    green_transportation FLOAT(5) NOT NULL,
    envir FLOAT(5) NOT NULL,
    information_management FLOAT(5) NOT NULL,
    state INT(1) NOT NULL,
    CONSTRAINT pk_t_envir_benefit_2_id PRIMARY KEY (id),
    CONSTRAINT fk_t_envir_benefit_2_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_envir_benefit_2_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_envir_benefit_2_state CHECK (state IN(1,2))
);

SELECT * FROM t_envir_benefit_2;
delete from t_envir_benefit_2 where id = 2;