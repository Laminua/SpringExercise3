package org.example.repositories;

import org.example.accounts.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilesRepository extends JpaRepository<UserProfile, Integer> {
    UserProfile queryDistinctByUsername(String username);
}
