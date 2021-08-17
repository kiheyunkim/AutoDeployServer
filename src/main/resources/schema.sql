CREATE TABLE IF NOT EXISTS DEPLOY_HISTORY
(
    ID             INT AUTO_INCREMENT PRIMARY KEY,
    DEPLOY_STAGE   VARCHAR(15),
    DEPLOY_STATE   VARCHAR(15),
    SCENARIO_NAME  VARCHAR(100),
    BUILD_TARGET   VARCHAR(100),
    DEPLOY_FROM    VARCHAR(50),
    DEPLOY_TO      VARCHAR(50),
    EXECUTION_DATE DATETIME
);

CREATE TABLE IF NOT EXISTS GITHUB_INFO
(
    ID           INT AUTO_INCREMENT PRIMARY KEY,
    REPO_ALIAS   VARCHAR(100),
    USER_NAME    VARCHAR(255),
    ACCESS_TOKEN VARCHAR(255)
)