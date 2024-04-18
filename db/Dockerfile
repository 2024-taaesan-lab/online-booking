FROM postgres:16

# Naming default database, user, and password
ENV POSTGRES_DB ps_db
ENV POSTGRES_USER ps_user
ENV POSTGRES_PASSWORD p@ssw0rd

# Executing SQL file on startup
COPY booking.sql /docker-entrypoint-initdb.d/