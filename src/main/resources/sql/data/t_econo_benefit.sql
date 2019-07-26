/*CREATE TABLE t_econo_benefit
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    operation_performance FLOAT(5) NOT NULL,
    state INT(1) NOT NULL,
    CONSTRAINT pk_t_econo_benefit_id PRIMARY KEY (id),
    CONSTRAINT fk_t_econo_benefit_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_econo_benefit_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_econo_benefit_4_state CHECK (state IN(1,2))
);


SELECT * FROM t_econo_benefit;
delete from t_econo_benefit where id = 4;*/
ALTER TABLE t_econo_benefit CHANGE opreation_performance operation_performance FLOAT(5) NOT NULL;