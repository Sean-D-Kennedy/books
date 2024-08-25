CREATE TABLE IF NOT EXISTS `book` (
    `id` int AUTO_INCREMENT  PRIMARY KEY,
    `book_title` varchar(100) NOT NULL,
    `authors` varchar(100) NOT NULL,
    `publisher` varchar(100) NOT NULL,
    `isbn` varchar(30) NOT NULL,
    `year_published` integer NOT NULL,
    `price` integer NOT NULL
    );
