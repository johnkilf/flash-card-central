# Learner Service

## Building


Building docker image:
```
./gradlew bootBuildImage
```


Running docker image:
```
docker run -p 8000:8000 notjohn/learner-service:0.0.1-SNAPSHOT
```

Pushing to remote docker repo:
```
docker push notjohn/learner-service:0.0.1-SNAPSHOT
```


Deploying to kubernetes cluster:
```
kubectl apply -f deployment.yaml
```
