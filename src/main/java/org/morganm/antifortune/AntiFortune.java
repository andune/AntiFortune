package org.morganm.antifortune;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;
import org.morganm.antifortune.util.Debug;
import org.morganm.antifortune.util.JarUtils;

public class AntiFortune extends JavaPlugin
{
	private static final Logger log = Logger.getLogger(AntiFortune.class.toString());
	private static final String logPrefix = "[AntiFortune] ";
	
	private Debug debug;
	private JarUtils jarUtils;
	private int buildNumber = -1;
	
	@Override
	public void onEnable() {
    	Debug.getInstance().init(log, logPrefix, "plugins/AntiFortune/debug.log", false);
		debug = Debug.getInstance();
    	jarUtils = new JarUtils(this, getFile(), log, logPrefix);
		buildNumber = jarUtils.getBuildNumber();
		Debug.getInstance().setDebug(getConfig().getBoolean("debug", false));
		
		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);
		
		info("version "+getDescription().getVersion()+", build "+buildNumber+" is enabled");
	}
	
	@Override
	public void onDisable() {
		info("version "+getDescription().getVersion()+", build "+buildNumber+" is disabled");
	}
	
	/** Log a message.
	 * 
	 * @param msg
	 */
	public void info(String msg) {
		log.info(logPrefix+msg);
	}
	
	public void debug(Object...args) {
		debug.debug(args);
	}
}
