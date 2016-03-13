use yr789; 
DROP TABLE IF EXISTS recharge_flow; 
CREATE TABLE recharge_flow (
	flow_id bigint,
	local_no VARCHAR(400),
	out_biz_no VARCHAR(400),
	pay_type VARCHAR(400),
	status tinyint,
	amount decimal,
	user_id bigint,
	yjf_account_id VARCHAR(400),
	bank_name VARCHAR(400),
	bank_account_no VARCHAR(400),
	bank_acount_name VARCHAR(400),
	pay_time datetime,
	pay_finish_time datetime,
	pay_memo VARCHAR(400),
	rem1 VARCHAR(400)
);