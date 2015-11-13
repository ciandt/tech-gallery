module.exports = function () {

  this.sendEndorsementEvent = function(techName, endorsedEmail){
    ga('send', 'event', 'Endorsement', techName, endorsedEmail);
  }

  this.sendSkillEvent = function(name, skill){
    if(skill){
      if(skill === 1){
        ga('send', 'event', 'Skill', name, 'Newbie');
      }
      if(skill === 2){
        ga('send', 'event', 'Skill', name, 'Initiate');
      }
      if(skill === 3){
        ga('send', 'event', 'Skill', name, 'Padawan');
      }
      if(skill === 4){
        ga('send', 'event', 'Skill', name, 'Knight');
      }
      if(skill === 5){
        ga('send', 'event', 'Skill', name, 'Jedi');
      }
    }
  }

  this.sendCommentEvent = function(techName){
    ga('send', 'event', 'Comment', 'comment_add', techName);
  }

  this.sendRecommendationEvent = function(techName, recommend){
    if(recommend === true){
      ga('send', 'event', 'Recommendation', 'recommendation_positive', techName);
    }
    if(recommend === false){
      ga('send', 'event', 'Recommendation', 'recommendation_negative', techName);
    }
  }

  this.trackUser = function(userEmail) {
    ga('create', 'UA-60744312-3', 'auto');
    ga('set', '&uid', userEmail);
    ga('set', 'contentGroup1', userEmail);
    ga('set', 'dimension1', userEmail);
    ga('send', 'pageview');
  }

  this.trackTechnologyAcess = function(techId){
    ga('send', 'event', 'TechGalleryEvents', 'technology_acess', techId);
  }
}

