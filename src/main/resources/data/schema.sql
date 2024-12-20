CREATE TABLE IF NOT EXISTS test 
(
	reference_id character varying(255) 
);

CREATE TABLE IF NOT EXISTS quiz_questions (
    difficulty VARCHAR(255),
    category VARCHAR(255),
    question VARCHAR(255),
    correct_answer BOOLEAN,
    qid INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (qid)
);