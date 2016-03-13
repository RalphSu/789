use yr789; 
DROP TABLE IF EXISTS division_template_loan; 
CREATE TABLE division_template_loan (
	base_id BIGINT,
	invest_template_id BIGINT,
	repay_template_id BIGINT
);