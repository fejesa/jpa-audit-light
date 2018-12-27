CREATE TABLE foo (
    id bigint,
    name character varying(255),
    creation_time timestamp without time zone,
    modification_time timestamp without time zone,
    created_by bigint,
    modified_by bigint,
    CONSTRAINT pk_foo PRIMARY KEY (id)
);

-- The audit table. Filled by the audit function.
CREATE TABLE a_foo (
    id bigint,
    name character varying(255),
    creation_time timestamp without time zone,
    modification_time timestamp without time zone,
    created_by bigint,
    modified_by bigint,
    operation character varying(10)
);

-- Fill the audit table when a business record is changed.
CREATE FUNCTION foo_audit() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  begin
    if TG_OP = 'DELETE' then
      insert into a_foo values(old.*, TG_OP);
    elsif TG_OP = 'UPDATE' then
      insert into a_foo values(new.*, TG_OP);
    elsif TG_OP = 'INSERT' then
      insert into a_foo values(new.*, TG_OP);
    end if;
    return null;
  end;
$$;

CREATE TRIGGER t_foo_audit AFTER INSERT OR DELETE OR UPDATE ON foo FOR EACH ROW EXECUTE PROCEDURE foo_audit();

CREATE SEQUENCE id_sequence
    START WITH 1000
    INCREMENT BY 1
    MINVALUE 1000
    MAXVALUE 100000000
    CACHE 1;