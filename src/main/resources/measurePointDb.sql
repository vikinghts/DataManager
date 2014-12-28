// postgress syntax
create table MEASUREPOINT (
    id SERIAL PRIMARY KEY,
    tDPower INT default NULL,
    tPPower INT default NULL,
    cpower  INT default NULL,
    tGas INT default NULL,
    mDateTime timestamp default NULL
);
