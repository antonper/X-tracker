package com.xtracker.android.caching;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.xtracker.android.objects.Track;
import com.xtracker.android.objects.Point;
import com.xtracker.android.objects.Jump;

import com.xtracker.android.caching.TrackDao;
import com.xtracker.android.caching.PointDao;
import com.xtracker.android.caching.JumpDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig trackDaoConfig;
    private final DaoConfig pointDaoConfig;
    private final DaoConfig jumpDaoConfig;

    private final TrackDao trackDao;
    private final PointDao pointDao;
    private final JumpDao jumpDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        trackDaoConfig = daoConfigMap.get(TrackDao.class).clone();
        trackDaoConfig.initIdentityScope(type);

        pointDaoConfig = daoConfigMap.get(PointDao.class).clone();
        pointDaoConfig.initIdentityScope(type);

        jumpDaoConfig = daoConfigMap.get(JumpDao.class).clone();
        jumpDaoConfig.initIdentityScope(type);

        trackDao = new TrackDao(trackDaoConfig, this);
        pointDao = new PointDao(pointDaoConfig, this);
        jumpDao = new JumpDao(jumpDaoConfig, this);

        registerDao(Track.class, trackDao);
        registerDao(Point.class, pointDao);
        registerDao(Jump.class, jumpDao);
    }
    
    public void clear() {
        trackDaoConfig.getIdentityScope().clear();
        pointDaoConfig.getIdentityScope().clear();
        jumpDaoConfig.getIdentityScope().clear();
    }

    public TrackDao getTrackDao() {
        return trackDao;
    }

    public PointDao getPointDao() {
        return pointDao;
    }

    public JumpDao getJumpDao() {
        return jumpDao;
    }

}
