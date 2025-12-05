Table state {
id BIGINT [pk, increment]
name VARCHAR(50)
year INT
election_id VARCHAR(10) [ref: > election.id]
}

Table election {
id VARCHAR(10) [pk]
state_id BIGINT [ref: > state.id]
name VARCHAR(50)
}

Table constituency {
id BIGINT [pk]
name VARCHAR(50)
state_id BIGINT [ref: > state.id]
}

Table municipality {
id BIGINT [pk]
name VARCHAR(50)
constituency_id BIGINT [ref: > constituency.id]
}

Table party {
id BIGINT [pk, increment]
name VARCHAR(50)
election_id VARCHAR(10) [ref: > election.id]
}

Table candidate {
id BIGINT [pk, increment]
name VARCHAR(50)
party_id BIGINT [ref: > party.id]
}

Table candidate_constituency {
candidate_id BIGINT [ref: > candidate.id]
constituency_id BIGINT [ref: > constituency.id]
}

Table party_constituency {
party_id BIGINT [ref: > party.id]
constituency_id BIGINT [ref: > constituency.id]
}