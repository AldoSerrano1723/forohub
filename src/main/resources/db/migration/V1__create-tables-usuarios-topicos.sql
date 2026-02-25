create table usuarios(

    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    password varchar(255) not null,

    primary key (id)
);

create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fecha_creacion datetime not null,
    status varchar (100) not null,
    autor_id bigint not null,
    curso varchar (100) not null,
    primary key (id),

    constraint fk_topicos_usuarios_id foreign key (autor_id) references usuarios(id)
);