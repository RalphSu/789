use yr789; 
DROP TABLE IF EXISTS amount_statistics_info_v_o; 
CREATE TABLE amount_statistics_info_v_o (
	dimentions VARCHAR(400),
	totalAmount BIGINT,
	paidAmount BIGINT,
	toPayAmount BIGINT
);