package com.jersey.repository;

import com.jersey.model.Roster;
import com.jersey.model.RosterItem;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;

public interface EmployeesDAO {
  @SqlUpdate("INSERT INTO roster (network_id, rostername, created_at, origin_id, origin_type) " +
          "VALUES (:networkId, :rosterName, :createdAt, :originId, :originType)")
  void insertRoster(@BindBean Roster roster);

  @SqlQuery("SELECT * FROM roster WHERE id = :id")
  @RegisterBeanMapper(Roster.class)
  Roster getRosterById(@Bind("id") long id) ;
  @SqlQuery("SELECT * FROM roster")
  @RegisterBeanMapper(Roster.class)
  List<Roster> getAllRosters();

  @SqlUpdate("UPDATE roster SET rostername = :rosterName WHERE id = :id")
  void updateRosterName(@Bind("id") long id, @Bind("rosterName") String rosterName);

  @SqlUpdate("DELETE FROM roster WHERE id = :id")
  void deleteRosterById(@Bind("id") long id);

  @SqlQuery("SELECT * FROM roster_item WHERE id = :id")
  RosterItem findById(@Bind("id") long id);

  @SqlQuery("SELECT * FROM roster_item WHERE roster_id = :rosterId")
  List<RosterItem> findByRosterId(@Bind("rosterId") long rosterId);

  @SqlUpdate("INSERT INTO roster_item (id, remote_id, emailaddress, roster_id) " +
          "VALUES (:id, :remoteId, :emailAddress, :rosterId)")
  void insert(@BindBean RosterItem rosterItemDao);
}
