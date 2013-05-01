package me.mrfrase3.chgp;

import com.laytonsmith.abstraction.MCLocation;
import com.laytonsmith.abstraction.bukkit.BukkitMCLocation;
import com.laytonsmith.abstraction.MCPlayer;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.CHVersion;
import com.laytonsmith.core.ObjectGenerator;
import com.laytonsmith.core.Static;
import com.laytonsmith.core.constructs.CArray;
import com.laytonsmith.core.constructs.CBoolean;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.CommandHelperEnvironment;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;
import com.laytonsmith.core.functions.Exceptions;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class CHGP {
    @api(environments = {CommandHelperEnvironment.class})
    public static class has_gp_buildperm extends AbstractFunction {
        
        public Exceptions.ExceptionType[] thrown() {
            return null;
        }
        
        public boolean isRestricted() {
            return true;
        }
        
        public Boolean runAsync() {
            return false;
        }
        
        public Construct exec(Target t, Environment environment, Construct... args) throws ConfigRuntimeException {
            //Player player = (Player)args[0];
            Player player = Bukkit.getPlayer(args[0].val());
            CArray array = (CArray)args[1];
            MCLocation loc = ObjectGenerator.GetGenerator().location(array, null, t);
            Location location = ((BukkitMCLocation)loc).asLocation();
            
            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, false, null);
            //if(claim == null){
            //    return new CBoolean(true, t);
            //}
            String errorMessage = claim.allowBuild(player);
            
            if (errorMessage == null){
                return new CBoolean(true, t);
            } else {
                return new CBoolean(false, t);
            }
        }
        
        public String getName() {
            return getClass().getSimpleName();
        }
        
        public Integer[] numArgs() {
            return new Integer[]{2};
        }
        
        public String docs() {
            return "boolean {player, location} See if a player can build at a given location.";
        }
        
        public CHVersion since() {
            return CHVersion.V3_3_1;
        }
    }
}
