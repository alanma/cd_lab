binaryRepository {
    baseUrl = 'http://localhost:8081/nexus'
    releaseUrl = "$baseUrl/content/repositories/releases"
    username = 'admin'
    password = 'admin123'
}

environments {
    test {
        server {
            hostname = 'localhost'
            port = 9292
            context = 'todo'
            username = 'manager'
            password = 'manager'
        }
    }

    uat {
        server {
            hostname = 'localhost'
            port = 8199
            context = 'todo'
            username = 'manager'
            password = 'manager'
        }
    }

    production {
        server {
            hostname = 'localhost'
            port = 8299
            context = 'todo'
            username = 'manager'
            password = 'manager'
        }
    }
}

//seleniumDriver='/Users/kalle/bin/chromedriver'