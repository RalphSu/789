use yr789; 
DROP TABLE IF EXISTS divsion_rule_role; 
CREATE TABLE divsion_rule_role (
	role_name varchar(400),
	role_id INT,
	division_phase varchar(400),
	division_rule DECIMAL
);