# Definition of Done

## General
- [ ] All acceptance criteria have been checked off
- [ ] The work is (technically) documented and relevant for fellow developers (ERD, UML, learning process, testing, test results)
- [ ] The work is written in standard English
- [ ] The work is in the GitLab repository
- [ ] The work has been reviewed by at least 1 person, preferably 2
- [ ] The work follows the **Think-Make-Check (TMC)** principle
- [ ] The code follows the HBO-ICT coding conventions
- [ ] The code has been manually functionally tested for errors
- [ ] The code works without errors under normal use
- [ ] The web application is usable on both **mobile** and **desktop** devices

## Backend
- [ ] Preferably use **DTOs**
- [ ] Do **not** use the builder pattern
- [ ] Use **custom exceptions** for the endpoints
- [ ] Do **not** use Lombok
- [ ] Do **not** use Spring Security
- [ ] Backend validation is required (not only in the frontend)
- [ ] Do **not** use integration tests in the backend (no `@SpringBootTest`)
- [ ] **Mocking** of services and/or repositories is required
- [ ] Sorting and filtering must be done in the backend
- [ ] Use **Mermaid.js** notation for UML diagrams and place them in the repo under `/docs`
- [ ] Use the provided **XML parser** for the election data  