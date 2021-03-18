## Part 1: Test it with SQL
-- id (int PK), employer (varchar255), name (varchar255), skills (varchar255)

## Part 2: Test it with SQL
-- write a query to list the names of the employers in St. Louis City
SELECT name FROM employer WHERE location = "St. Louis City";

## Part 3: Test it with SQL
-- remove the job table
DROP TABLE job;

## Part 4: Test it with SQL
-- return a list of names and desc of all skills attached to jobs in alphabetical order. If a skill doesn't have a job listed, it shouldn't be included.
SELECT name, description FROM skill LEFT JOIN job_skills ON skill.id = job_skills.skills_id WHERE job_skills.job_id IS NOT NULL ORDER BY name ASC;