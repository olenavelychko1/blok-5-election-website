# Endpoint overview

## Backend

[//]: # (Fill in backend endpoints)

---

## XML Parser
``GET /candidates``

### What does this endpoint?
This endpoint will parse the candidate lists from XML to a list of parties. It can filter on "kieskring" and "year".

### What kind of response will the endpoint send back
The endpoint will send back a list of parties with candidates included.
```
// The party entity
interface Party {
    id: int
    name: String
    candidates: List<Candidate>
}

// The candidate entity
interface Candidate {
    id: int
    initials: String
    firstName: String
    lastName: String
    gender: enum("male", "female") || null
    locality: String
}
```

### What kind of entity or parameters receives this endpoint? Is it a GET, POST, PUT, PATCH, DELETE request?
This is a GET endpoint. This endpoint can receive 2 optional parameters: "kieskring" and "year".

---
