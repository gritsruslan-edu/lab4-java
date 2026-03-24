CREATE TABLE books (
   id BIGSERIAL PRIMARY KEY,
   type VARCHAR(50) NOT NULL,
   title VARCHAR(255) NOT NULL,
   author VARCHAR(255) NOT NULL,
   year INTEGER NOT NULL,
   pages INTEGER NOT NULL,
   genre VARCHAR(100) NOT NULL,
   quantity INTEGER NOT NULL,
   file_format VARCHAR(100),
   file_size DOUBLE PRECISION,
   cover_type VARCHAR(100),
   print_run INTEGER,
   narrator VARCHAR(255),
   duration_minutes INTEGER,
   field_of_science VARCHAR(255),
   peer_reviewed BOOLEAN
);