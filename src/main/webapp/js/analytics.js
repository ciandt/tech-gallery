function sendEndorsementEvent(techName, endorsedEmail){
	ga('send', 'event', 'Endorsement', techName, endorsedEmail);
}

function sendSkillEvent(skill){
	if(skill){
		if(skill === "Newbie"){
			ga('send', 'event', 'Skill', $scope.name, 'Newbie');
		}
		if(skill === "Initiate"){
			ga('send', 'event', 'Skill', $scope.name, 'Initiate');
		}
		if(skill === "Padawan"){
			ga('send', 'event', 'Skill', $scope.name, 'Padawan');
		}
		if(skill === "Knight"){
			ga('send', 'event', 'Skill', $scope.name, 'Knight');
		}
		if(skill === "Jedi"){
			ga('send', 'event', 'Skill', $scope.name, 'Jedi');
		}
	}
}

function sendCommentEvent(techName){
	ga('send', 'event', 'Comment', 'comment_add', techName);
}

function sendRecommendationEvent(techName, recommend){
	if(recommend === true){
		ga('send', 'event', 'Recommendation', 'recommendation_positive', techName);
	}
	if(recommend === false){
		ga('send', 'event', 'Recommendation', 'recommendation_negative', techName);
	}
}
