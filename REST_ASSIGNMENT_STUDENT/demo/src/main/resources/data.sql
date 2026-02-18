INSERT INTO student
(reg_no, roll_no, name, std, school, gender, percentage)
VALUES
(1,101,'Aarav Sharma',10,'St. Xavier School','Male',35),
(2,102,'Diya Patel',10,'St. Xavier School','Female',42),
(3,103,'Rohan Mehta',9,'Delhi Public School','Male',48),
(4,104,'Ananya Singh',8,'Ryan International','Female',52),
(5,105,'Kabir Khan',7,'Podar International','Male',55),
(6,106,'Meera Iyer',6,'DAV Public School','Female',60),
(7,107,'Arjun Nair',5,'Modern School','Male',63),
(8,108,'Sara Shaikh',10,'St. Mary School','Female',65),
(9,109,'Vivaan Gupta',9,'Delhi Public School','Male',68),
(10,110,'Ishita Das',8,'Ryan International','Female',70),
(11,111,'Kunal Verma',7,'Podar International','Male',72),
(12,112,'Pooja Yadav',6,'DAV Public School','Female',74),
(13,113,'Aditya Joshi',5,'Modern School','Male',76),
(14,114,'Neha Kapoor',10,'St. Mary School','Female',80),
(15,115,'Rahul Desai',9,'St. Xavier School','Male',83),
(16,116,'Sneha Kulkarni',8,'Delhi Public School','Female',86),
(17,117,'Manav Jain',7,'Ryan International','Male',90),
(18,118,'Riya Choudhary',6,'Podar International','Female',27),
(19,119,'Dev Malhotra',5,'DAV Public School','Male',33),
(20,120,'Aisha Fernandes',10,'St. Mary School','Female',95)
ON CONFLICT (reg_no) DO NOTHING;
