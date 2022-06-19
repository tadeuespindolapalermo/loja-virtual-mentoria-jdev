SELECT constraint_name 
FROM information_schema.constraint_column_usage
WHERE table_name = 'usuarios_acesso'
AND column_name = 'acesso_id'
AND constraint_name <> 'unique_acesso_user';

-- Removendo constraint indevida criada pelo Hibernate
ALTER TABLE usuarios_acesso DROP CONSTRAINT "uk_8bak9jswon2id2jbunuqlfl9e";