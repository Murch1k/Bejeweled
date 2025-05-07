DROP TABLE IF EXISTS Score CASCADE;
CREATE TABLE Score (
                       ident SERIAL PRIMARY KEY,
                       player VARCHAR(255) NOT NULL,
                       game VARCHAR(255) NOT NULL,
                       points INTEGER NOT NULL,
                       playedOn TIMESTAMP NOT NULL
);
DROP TABLE IF EXISTS Score_time CASCADE;
CREATE TABLE score_time (
                            ident SERIAL PRIMARY KEY,
                            player VARCHAR(255) NOT NULL,
                            game VARCHAR(255) NOT NULL,
                            points INT NOT NULL,
                            timeLimit INT NOT NULL,
                            playedOn TIMESTAMP NOT NULL
);
DROP TABLE IF EXISTS Rating CASCADE;
CREATE TABLE Rating (
                        ident SERIAL PRIMARY KEY,
                        player VARCHAR(255) NOT NULL,
                        game VARCHAR(255) NOT NULL,
                        rating INTEGER NULL CHECK (rating >= 1 AND rating <= 5),
                        ratedOn TIMESTAMP NOT NULL,
                        UNIQUE (game, player)
);
DROP TABLE IF EXISTS Comment CASCADE;
CREATE TABLE Comment (
                         ident SERIAL PRIMARY KEY,
                         player VARCHAR(255) NOT NULL,
                         game VARCHAR(255) NOT NULL,
                         comment TEXT NOT NULL,
                         commentedOn TIMESTAMP NOT NULL
);