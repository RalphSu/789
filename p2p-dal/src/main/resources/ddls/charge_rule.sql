use yr789; 
DROP TABLE IF EXISTS charge_rule; 
CREATE TABLE charge_rule (
	charge_rule_id BIGINT,
	charge_template_id BIGINT,
	rule_method VARCHAR(400),
	start_amount BIGINT,
	end_amount BIGINT,
	charge_value DECIMAL
);