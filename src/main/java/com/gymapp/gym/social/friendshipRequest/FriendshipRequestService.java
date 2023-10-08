package com.gymapp.gym.social.friendshipRequest;

import com.gymapp.gym.social.Social;
import com.gymapp.gym.social.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendshipRequestService {
    @Autowired
    private FriendshipRequestRepository repository;
    @Autowired
    private SocialService socialService;


    public Optional<FriendshipRequest> getFriendShipRequestByReceiverAndSender(Social userSocial, Social friendSocial) {

        return repository.findByReceiverAndSender(userSocial, friendSocial);

    }

    public void createFriendShipRequest(int userSocialId, int friendSocialId ) {
        Social userSocial = socialService.getById(userSocialId);
        Social friendSocial = socialService.getById(friendSocialId);

        if (userSocial == null || friendSocial == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        Optional<FriendshipRequest> existingFriendshipRequest = repository.findByReceiverAndSender(friendSocial, userSocial);

        if (existingFriendshipRequest.isPresent()) {
            return;
        }

        FriendshipRequest friendshipRequest = new FriendshipRequest();
        friendshipRequest.setSender(userSocial);
        friendshipRequest.setReceiver(friendSocial);
        friendshipRequest.setStatus(FriendshipStatus.PENDING);

        repository.save(friendshipRequest);
    }

    public FriendshipRequest acceptFriendshipRequestByUsers(Integer userSocialId, Integer friendSocialId) {
        Social userSocial = socialService.getById(userSocialId);
        Social friendSocial = socialService.getById(friendSocialId);

        if ( friendSocial == null) {
            throw new IllegalArgumentException("One of the users doesn't exist: userSocialId=" + userSocialId + ", friendSocialId=" + friendSocialId);
        }

        Optional<FriendshipRequest> optionalFriendshipRequest = repository.findByReceiverAndSender(userSocial, friendSocial);

        if (optionalFriendshipRequest.isEmpty()) {
           throw new IllegalArgumentException("No friend request was found");
        }

        if (optionalFriendshipRequest.get().getStatus().equals(FriendshipStatus.ACCEPTED)) {
            throw new IllegalStateException("Friend request is already accepted");
        }

        FriendshipRequest friendshipRequest = optionalFriendshipRequest.get();

        friendshipRequest.setStatus(FriendshipStatus.ACCEPTED);

        return repository.save(friendshipRequest);
    }

    public Set<FriendshipRequest> getFriendRequestsByUser(Integer socialId) {
        Social user = socialService.getById(socialId);

        if (user == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        Set<FriendshipRequest> friendRequestsList = new HashSet<>();

        friendRequestsList.add(repository.getByReceiver(user));

        return friendRequestsList;
    }

    public Set<FriendshipRequest> getFriendRequestsByUserAndStatus(Integer socialId, FriendshipStatus status) {
        Social user = socialService.getById(socialId);

        if (user == null) {
            throw new IllegalArgumentException("User doesn't exist");
        }

        if (status == null) {
            throw new IllegalArgumentException("Status is null");
        }

        Set<FriendshipRequest> friendRequestsList =  repository.getByReceiverAndStatus(user, status);


        return friendRequestsList;
    }
}
