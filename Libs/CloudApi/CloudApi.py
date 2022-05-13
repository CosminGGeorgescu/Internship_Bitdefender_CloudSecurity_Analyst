import os
import pickle
import time
from random import randint


class __base_boto3_mockup_class:
    """
    Base class for the client.
    Used for mocking up a real cloud platform.
    Reproduces the random effect of requesting data from the platform.
    """

    def __init__(self) -> None:
        pass

    @staticmethod
    def _default_mockup_behaviour(file_name) -> dict:
        """
        Reads data from Boto3Mockup\file_name and returns it as a dictionary.

        :param file_name: the name of the file that contains the data to be read.
        :return: returns the data from file_name as a python dict
        """
        path = os.path.join(os.path.dirname(__file__), os.path.join("CloudApiData", file_name))

        with open(path, "rb") as f:
            data = pickle.load(f)

        time.sleep(randint(0, 100) / 100)

        return data


class client(__base_boto3_mockup_class):
    """
    MOCKUP!
    Create a low-level service client by name using the default session.
    """

    def __init__(self, service_name) -> None:
        """
        Initializes the client.

        :param service_name: Name of the service to be used
        """
        super().__init__()
        self.service_name = service_name
        self.__credReportGenTime = None
        self.__credReportAvailable = False

    def list_users(self, Marker='m.1'):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_users

        List the IAM users on the platform.

        :param Marker: Use this parameter only when paginating results and only after you receive a
        response indicating that the results are truncated. Set it to the value of the Marker element in
        the response that you received to indicate where the next call should start.

        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_users)
        """
        v = Marker.split(".")[1]
        return super()._default_mockup_behaviour("users_list_" + str(v) + ".json")

    def list_access_keys(self, UserName="admin_api_access"):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_access_keys

        Returns information about the access key IDs associated with the specified IAM user. If there is none,
        the operation returns an empty list.

        :param UserName: The name of the user.
        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_access_keys)
        """
        if UserName is None or UserName == "":
            raise Exception("No user name supplied")

        accessKeys = super()._default_mockup_behaviour("access_keys.json")
        if UserName not in accessKeys.keys():
            raise Exception(f"User {UserName} not available in the access keys list")
        return accessKeys[UserName]

    def generate_credential_report(self):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.generate_credential_report

        Generates a credential report for the Amazon Web Services account.
        For more information about the credential report, see
        https://docs.aws.amazon.com/IAM/latest/UserGuide/id_credentials_getting-report.html.

        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.generate_credential_report)
        """
        if self.__credReportGenTime is None:
            self.__credReportGenTime = time.perf_counter()
            return {'State': 'STARTED',
                    'ResponseMetadata': {'RequestId': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e', 'HTTPStatusCode': 200,
                                         'HTTPHeaders': {'x-amzn-requestid': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e',
                                                         'content-type': 'text/xml', 'content-length': '327',
                                                         'date': 'Mon, 21 Mar 2022 07:27:07 GMT'}, 'RetryAttempts': 0}}
        if time.perf_counter() - self.__credReportGenTime > 10:
            self.__credReportAvailable = True
        if self.__credReportAvailable:
            return {'State': 'COMPLETE',
                    'ResponseMetadata': {'RequestId': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e', 'HTTPStatusCode': 200,
                                         'HTTPHeaders': {'x-amzn-requestid': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e',
                                                         'content-type': 'text/xml', 'content-length': '327',
                                                         'date': 'Mon, 21 Mar 2022 07:27:07 GMT'}, 'RetryAttempts': 0}}
        return {'State': 'INPROGRESS',
                'ResponseMetadata': {'RequestId': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e', 'HTTPStatusCode': 200,
                                     'HTTPHeaders': {'x-amzn-requestid': 'f5138586-9d4f-4a74-ac5e-1f93a3f5a34e',
                                                     'content-type': 'text/xml', 'content-length': '327',
                                                     'date': 'Mon, 21 Mar 2022 07:27:07 GMT'}, 'RetryAttempts': 0}}

    def get_credential_report(self):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.get_credential_report

        Retrieves a credential report for the Amazon Web Services account. For more information about the
        credential report, see Getting credential reports in the IAM User Guide .

        :return dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.get_credential_report)
        """
        if self.__credReportAvailable:
            return super()._default_mockup_behaviour("get_credential_report.json")
        else:
            raise Exception("No credential report generated!")

    def list_attached_user_policies(self, UserName=""):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_attached_user_policies

        Lists all managed policies that are attached to the specified IAM user.

        :param UserName: [REQUIRED] The name (friendly name, not ARN) of the user to list attached policies for.
        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_attached_user_policies)
        """
        if UserName is None or UserName == "":
            raise Exception("No UserName supplied")
        attached_user_policies = super()._default_mockup_behaviour("attached_user_policies.json")

        if UserName not in attached_user_policies.keys():
            raise Exception(f"Policy with user name {UserName} not available")

        return attached_user_policies[UserName]

    def list_user_policies(self, UserName=""):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_user_policies

        Lists the names of the inline policies embedded in the specified IAM user.
        :param UserName: [REQUIRED] The name of the user to list policies for.
        :return: (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_user_policies)
        """
        if UserName is None or UserName == "":
            raise Exception("No UserName supplied")

        user_policies = super()._default_mockup_behaviour("user_policies.json")

        if UserName not in user_policies.keys():
            raise Exception(f"Policy with user name {UserName} not available")

        return user_policies[UserName]

    def list_policies(self):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_policies

        Lists all the managed policies that are available in your Amazon Web Services account, including your own
        customer-defined managed policies and all Amazon Web Services managed policies.

        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_policies)
        """
        return super()._default_mockup_behaviour("policies.json")

    def list_entities_for_policy(self, PolicyArn=""):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_entities_for_policy
        Lists all IAM users, groups, and roles that the specified managed policy is attached to.

        :param PolicyArn: [REQUIRED] The Amazon Resource Name (ARN) of the IAM policy for which you want the versions.
        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_entities_for_policy)
        """
        if PolicyArn is None or PolicyArn == "":
            raise Exception("No PolicyArn supplied")

        policies = super()._default_mockup_behaviour("entities_for_policy.json")

        if PolicyArn not in policies.keys():
            raise Exception(f"Policy with arn {PolicyArn} not available")

        return policies[PolicyArn]

    def list_server_certificates(self):
        """
        Mockup for:
         https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_server_certificates

        Lists the server certificates stored in IAM that have the specified path prefix. If none exist, the operation returns an empty list.
        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.list_server_certificates)
        """
        return super()._default_mockup_behaviour("server_crtificates.json")

    def get_policy_version(self, PolicyArn="", VersionId=""):
        """
        Mockup for:
        https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.get_policy_version

        :param PolicyArn: [REQUIRED] The Amazon Resource Name (ARN) of the managed policy that you want information about.
        :param VersionId: [REQUIRED] Identifies the policy version to retrieve.
        :return: dict (see https://boto3.amazonaws.com/v1/documentation/api/latest/reference/services/iam.html#IAM.Client.get_policy_version)
        """
        if PolicyArn is None or PolicyArn == "":
            raise Exception("No PolicyArn supplied")

        if VersionId is None or VersionId == "":
            raise Exception("No VersionId supplied")

        policy_version = super()._default_mockup_behaviour("policies_versions.json")['PoliciesVersions']

        if PolicyArn not in policy_version.keys():
            raise Exception(f"{PolicyArn} is not a valid arn")

        if VersionId not in policy_version[PolicyArn]:
            raise Exception(f"{VersionId} is not a valid version id")

        return policy_version[PolicyArn][VersionId]

    def get_account_password_policy(self):
        """
        TODO
        :return:
        """
        return super()._default_mockup_behaviour("account_password_policy.json")