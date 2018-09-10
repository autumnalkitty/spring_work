-- ��ǰ ���̺�
CREATE TABLE shop(
	num NUMBER PRIMARY KEY, --��ǰ��ȣ
	name VARCHAR2(30), --��ǰ�̸�
	price NUMBER, --��ǰ����
	stock NUMBER CHECK(stock >= 0) --����� 
);

-- �� ���� ���̺�
CREATE TABLE client_account(
	id VARCHAR2(30) PRIMARY KEY, -- ���� ���̵�
	money NUMBER CHECK(money >= 0), -- ���� �ܰ� 
	point NUMBER
);

-- �ֹ� ���̺�
CREATE TABLE client_order(
	num NUMBER PRIMARY KEY, -- �ֹ���ȣ
	id VARCHAR2(30), -- �ֹ� ���� ���̵�
	code NUMBER, -- �ֹ��� ��ǰ�� ��ȣ 
	addr VARCHAR2(50) -- ��� �ּ�
);

-- �ֹ� ���̺� ����� ������
CREATE SEQUENCE client_order_seq;

-- sample data
INSERT INTO shop (num, name, price, stock)
VALUES(1, '���', '1000', 5);

INSERT INTO shop (num, name, price, stock)
VALUES(2, '�ٳ���', '2000', 5);

INSERT INTO shop (num, name, price, stock)
VALUES(3, '��', '3000', 5);

INSERT INTO client_account (id, money, point)
VALUES('superman', 10000, 0);

INSERT INTO client_account (id, money, point)
VALUES('batman', 10000, 0);