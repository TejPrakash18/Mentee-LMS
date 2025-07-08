package com.tej.smart_lms.services;




import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> updateProfile(String username, String newName, String email,
                                        String location, String college, String education,String linkedin, String github, Set<String> skills) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (location != null) user.setLocation(location);
            if (skills != null) {
                Set<String> normalizedSkills = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                for (String skill : skills) {
                    if (skill != null) {
                        skill = skill.trim();
                        if (!skill.isEmpty()) {
                            // Capitalize each word
                            String[] words = skill.toLowerCase().split("\\s+");
                            StringBuilder formattedSkill = new StringBuilder();
                            for (String word : words) {
                                if (!word.isEmpty()) {
                                    formattedSkill.append(Character.toUpperCase(word.charAt(0)))
                                            .append(word.substring(1))
                                            .append(" ");
                                }
                            }
                            normalizedSkills.add(formattedSkill.toString().trim());
                        }
                    }
                }
                user.setSkills(normalizedSkills);
            }



            if (newName != null) user.setName(newName);
            if (college != null) user.setCollege(college);
            if(college!= null) user.setEducation(education);
            if (email != null) user.setEmail(email);
            if(linkedin!=null) user.setLinkedin(linkedin);
            if(github!=null) user.setGithub(github);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }


    private static final List<String> AVATARS = List.of(
            "/avatars/avatar.png",
            "/avatars/avatar2.png",
            "/avatars/avatar3.png",
            "/avatars/avatar4.png",
            "/avatars/avatar5.png",
            "/avatars/avatar6.png",
            "/avatars/avatar7.png",
            "/avatars/avatar8.png",
            "/avatars/avatar9.png",
            "/avatars/avatar10.png"
    );

    public User assignAvatarIfAbsent(String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if (user.getAvatarUrl() == null || user.getAvatarUrl().isBlank()) {
                        String randomAvatar = getRandomAvatar();
                        user.setAvatarUrl(randomAvatar);
                        return userRepository.save(user);
                    }
                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }


    private String getRandomAvatar() {
        Random random = new Random();
        return AVATARS.get(random.nextInt(AVATARS.size()));
    }

    public String getAvatarUrl(String username) {
        return userRepository.findByUsername(username)
                .map(User::getAvatarUrl)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}