package com.nzb.netty3.server.module.player.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.nzb.netty3.server.module.player.dao.entity.Player;

@Component
public class PlayerDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;

	public Player getPlayerById(long playerId) {
		return hibernateTemplate.get(Player.class, playerId);
	}

	public Player getPlayerByName(final String playerName) {
		return hibernateTemplate.execute(new HibernateCallback<Player>() {

			@SuppressWarnings("deprecation")
			@Override
			public Player doInHibernate(Session session) throws HibernateException, SQLException {
				NativeQuery<Player> query = session.createNativeQuery("SELECT * FROM player WHERE playerName = ?");
				query.setParameter(0, playerName);
				query.addEntity(Player.class);

				List<Player> list = query.list();
				if (list == null || list.isEmpty()) {
					return null;
				}
				return list.get(0);
			}
		});
	}

	public Player createPlayer(Player player) {
		long playerId = (long) hibernateTemplate.save(player);
		player.setPlayerId(playerId);
		return player;
	}

}
