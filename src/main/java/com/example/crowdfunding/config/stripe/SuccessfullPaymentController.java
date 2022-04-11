package com.example.crowdfunding.config.stripe;

import com.example.crowdfunding.config.GoogleSheetsService;
import com.example.crowdfunding.config.SendGridService;
import com.example.crowdfunding.donor.Donor;
import com.example.crowdfunding.project.Project;
import com.example.crowdfunding.project.ProjectRepository;
import com.example.crowdfunding.reward.Reward;
import com.example.crowdfunding.reward.RewardService;
import com.example.crowdfunding.user.User;
import com.example.crowdfunding.user.UserRepository;
import com.google.api.services.drive.model.File;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class SuccessfullPaymentController {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RewardService rewardService;

    @GetMapping(path = "/api/v1.0/payments/success/{projectId}&{amount}&{userId}")
    public void successfulPayment(@PathVariable("projectId") String projectId, @PathVariable("amount") long amount,
                                  @PathVariable("userId") String userId) throws IOException, GeneralSecurityException {
        //Find project
        Project project = projectRepository.findById(new ObjectId(projectId));

        //Find donor in user repo and generate user's reward
        User user = userRepository.findById(new ObjectId(userId));
        Reward reward = rewardService.createReward(user, project);
        userRepository.save(user);

        //Send email to user
        SendGridService.sendRewardsEmail(user.getEmail(), project.getTitle(), reward.getName(), reward.getId().toString());


        // Add donated amount and donor to project
        project.addDonationToAmountRaised((double)amount);
        project.addToDonorsList(
                new Donor(user, amount, reward)
        );
        projectRepository.save(project);

        if (project.getAmountRaised() >= project.getGoal()) {

            //Create google sheets spreadsheet
            GoogleSheetsService googleSheetsService = new GoogleSheetsService();
            File spreadsheet = googleSheetsService.create();

            // Add Donors' info to the created spreadsheet
            AppendValuesResponse appendValues = googleSheetsService.addValues(spreadsheet.getId(), project.getProjectDonors());

            //Get spreadsheet url and send to the project owner
//            var spreadSheetUrl = googleSheetsService.getSheetUrl(spreadsheet.getId());
            String projectOwnerEmail = project.getProjectOwner().getOwner().getEmail();
            SendGridService.sendDonorListEmail(projectOwnerEmail, spreadsheet.getId());
        }
    }


}
