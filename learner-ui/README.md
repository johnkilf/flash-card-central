# Learner UI

## Running 
```
npm start
```

## Building docker image
```
docker build -t notjohn/learner-ui:0.0.1-SNAPSHOT .
```

If running on M1 mac:
```
docker buildx build --platform linux/amd64,linux/arm64/v8 -t notjohn/learner-ui:0.0.1-SNAPSHOT .
```

## Running docker image locally
```
docker run -p 3000:3000 notjohn/learner-ui:0.0.1-SNAPSHOT
```

## Pushing to remote docker repo:
```
docker push notjohn/learner-ui:0.0.1-SNAPSHOT
```


## Deploying to kubernetes cluster:
```
kubectl apply -f deployment.yaml
```