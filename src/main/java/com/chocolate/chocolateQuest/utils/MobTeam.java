package com.chocolate.chocolateQuest.utils;

import net.minecraft.scoreboard.Team;

public class MobTeam extends Team {

   String teamName;


   public MobTeam(String teamName) {
      this.teamName = teamName;
   }

   public boolean isSameTeam(Team team) {
      return team == null?false:this.getRegisteredName().equals(team.getRegisteredName());
   }

   public boolean func_98297_h() {
      return false;
   }

   public boolean getAllowFriendlyFire() {
      return false;
   }

   public String formatString(String var1) {
      return this.teamName;
   }

   public String getRegisteredName() {
      return this.teamName;
   }
}
